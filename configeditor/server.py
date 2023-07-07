from cProfile import label
import datetime
from enum import Enum
from gc import disable
from pathlib import Path
import os
import json

import streamlit as st
import streamlit_pydantic as sp
from pydantic import BaseModel, Field
from PIL import Image

head_dir_path = "."
if os.environ.get("head_dir"):
    head_dir_path = os.environ.get("head_dir")
head_dir = Path(head_dir_path) 


class Speaker(str, Enum):
    mute = "mute"
    zhy = "zhy"


class LLMModel(str, Enum):
    gpt3_5_turbo_16k = "gpt-3.5-turbo-16k"
    gpt3_5_turbo = "gpt-3.5-turbo"
    gpt4 = "gpt-4"


class BotModel(BaseModel):
    id: str = Field(
        ...,
        title="id",
        description="一旦建立，不可修改",
        readOnly=True
    )
    name: str = Field(
        "storyteller",
        title="名称",
        description="展示给玩家的昵称"
    )
    description: str = Field(
        "bot",
        title="描述",
        description="展示给玩家的描述"
    )
    model: LLMModel = Field(
        LLMModel.gpt4,
        title="语言模型",
        description="使用的大语言模型"
    )
    speaker: Speaker = Field(
        Speaker.zhy,
        title="音色",
        description="语音选择,mute:无音色，zhy:曾海月"
    )
    greeting: str = Field(
        "你好",
        title="问好",
        description="bot主动说的第一句话",
        format="multi-line"
    )
    prompt: str = Field(
        "I want you to act as a storyteller. You will come up with entertaining stories that are engaging, "
        "imaginative and captivating for the audience.",
        title="提示词",
        description="注意token数规划，gpt-3.5是2048，格式为S:[system message]\nU:[user message]\nA:[assistant message]",
        format="multi-line",
        st_kwargs_height=300
    )

    info: str = Field(
        "",
        title="策划注释",
        description="策划自用，随便填",
        format="multi-line"
    )

    head: str = Field(
        "default.png",
        title = "自定义头像",
        description = "显示文件名",
        disabled = True
    )

    def to_profile_json(self):
        return self.json(exclude={'id', 'prompt', 'info'}, indent=4, ensure_ascii=False)

    def save_bot(self, bots_dir: Path):
        this_bot_dir = bots_dir / self.id
        if not this_bot_dir.exists():
            this_bot_dir.mkdir()

        (this_bot_dir / 'profile.json').write_text(self.to_profile_json(), encoding="utf8")
        if self.prompt:
            (this_bot_dir / 'prompt.txt').write_text(self.prompt, encoding="utf8")
        if self.info:
            (this_bot_dir / 'info.txt').write_text(self.info, encoding="utf8")

    @staticmethod
    def load_bot(this_bot_dir: Path):
        fp = (this_bot_dir / 'profile.json')
        if not fp.exists():
            return None
        profile = fp.read_text(encoding='utf8')

        fp = (this_bot_dir / 'prompt.txt')
        prompt = fp.read_text(encoding='utf8') if fp.exists() else ""
        fp = (this_bot_dir / 'info.txt')
        info = fp.read_text(encoding='utf8') if fp.exists() else ""

        kwargs = json.loads(profile)
        kwargs['id'] = this_bot_dir.name
        kwargs['prompt'] = prompt
        kwargs['info'] = info

        bot = BotModel.parse_obj(kwargs)
        return bot

    @staticmethod
    def delete_bot(delete_bot_dir: Path):
        (delete_bot_dir / 'profile.json').unlink()
        (delete_bot_dir / 'prompt.txt').unlink(missing_ok=True)
        (delete_bot_dir / 'info.txt').unlink(missing_ok=True)
        delete_bot_dir.rmdir()


class BotConfigEditor:
    def __init__(self, config: Path):
        self.config_dir = config
        self.bots_dir = config / 'bots'
        if 'bots' not in st.session_state:
            st.session_state.bots = {}
            for b in self.bots_dir.iterdir():
                if b.is_dir():
                    bot = BotModel.load_bot(b)
                    if bot:
                        st.session_state.bots[bot.id] = bot

        if 'cur' not in st.session_state:
            if len(st.session_state.bots) > 0:
                for bot in st.session_state.bots.values():
                    st.session_state.cur = bot
                    break

    def show(self):
        with st.sidebar:
            st.divider()
            st.header("bots")
            for bot_id, bot in st.session_state.bots.items():
                if st.button(bot_id):
                    st.session_state.cur = bot

            st.divider()
            with st.expander("new bot"):
                with st.form("new"):
                    new_bot_id = st.text_input("new bot id")
                    if st.form_submit_button("create"):
                        if len(new_bot_id) == 0:
                            st.error("名称为空，无法创建")
                        elif new_bot_id in st.session_state.bots:
                            st.error(f"{new_bot_id}重名，无法创建")
                        else:
                            st.session_state.cur = BotModel(id=new_bot_id)

            with st.expander("delete bot"):
                with st.form("delete"):
                    delete_bot_id = st.text_input("delete bot id")
                    delete_sure = st.text_input("are you sure", placeholder="yes! delete", help="输入yes! delete确认")
                    if st.form_submit_button("delete"):
                        if len(delete_bot_id) == 0:
                            st.error("名称为空，无法删除")
                        elif delete_bot_id not in st.session_state.bots:
                            st.error(f"{delete_bot_id}不在bots列表中，无法删除")
                        elif delete_sure != "yes! delete":
                            st.error("确认code输入不对，请输入yes! delete")
                        else:
                            self.delete_bot(delete_bot_id)
                            if 'cur' in st.session_state and st.session_state.cur.id == delete_bot_id:
                                del st.session_state['cur']
                            st.experimental_rerun()

        if 'cur' in st.session_state:
            with st.form(key="model_update"):
                cur = st.session_state.cur
                is_update = cur.id in st.session_state.bots
                intent = f"更新{cur.id}" if is_update else f"创建{cur.id}"
                st.header(intent)
                input = sp.pydantic_input(key=cur.id, model=cur)
                if os.path.exists(cur.head):
                    image = Image.open(head_dir / cur.head)
                    image_compoment = st.image(image, caption="头像")
                if st.form_submit_button(label=intent):
                    self.save_bot(cur, True)

            file_upload = st.file_uploader(label="头像图片", type=['png', 'jpg'], key="head_icon")
            if file_upload is not None:
                print(head_dir/file_upload.name)
                with open(head_dir/file_upload.name, 'wb') as f:
                    f.write(file_upload.getvalue())
                if cur.head != file_upload.name:
                    cur.head = file_upload.name
                    st.experimental_rerun()

    def save_bot(self, bot: BotModel, is_update: bool):
        bot.save_bot(self.bots_dir)
        st.session_state.bots[bot.id] = bot
        self.log('update' if is_update else 'create', bot.id)

    def delete_bot(self, delete_bot_id: str):
        BotModel.delete_bot(self.bots_dir / delete_bot_id)
        del st.session_state.bots[delete_bot_id]
        self.log('delete', delete_bot_id)

    def log(self, action: str, bot_id: str):
        with (self.config_dir / 'changelist.txt').open(mode='a') as fp:
            fp.write(f"{datetime.datetime.now()} {action} {bot_id}\n")


if __name__ == '__main__':
    editor = BotConfigEditor(Path('../configdir'))
    editor.show()
