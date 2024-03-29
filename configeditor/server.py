import argparse
import datetime
import json
import sys
from enum import Enum
from pathlib import Path

import streamlit as st
import streamlit_pydantic as sp
from PIL import Image
from pydantic import BaseModel, Field
import tiktoken


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
        title="自定义头像",
        description="显示文件名",
        disabled=True
    )

    artistModel: str = Field(
        "请指定一个有效的美术模型",
        title="美术模型",
        description="美术模型"
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
        if 'artistModel' not in kwargs:
            kwargs['artistModel'] = "请指定一个有效的美术模型"

        bot = BotModel.parse_obj(kwargs)
        return bot

    @staticmethod
    def delete_bot(delete_bot_dir: Path):
        (delete_bot_dir / 'profile.json').unlink()
        (delete_bot_dir / 'prompt.txt').unlink(missing_ok=True)
        (delete_bot_dir / 'info.txt').unlink(missing_ok=True)
        delete_bot_dir.rmdir()


class BotConfigEditor:
    def __init__(self, config_dir: Path, head_dir: Path):
        self.config_dir = config_dir
        self.head_dir = head_dir
        self.bots_dir = config_dir / 'bots'
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
            cur = st.session_state.cur
            is_update = cur.id in st.session_state.bots
            intent = f"更新{cur.id}" if is_update else f"创建{cur.id}"
            st.header(intent)

            file_upload = st.file_uploader(label="头像图片", type=['png', 'jpg'], key="head_icon")
            if file_upload:
                # 第一次upload，file_upload会一直存在，id会保持跟上次相同，当再次上传时id会递增，所以这里用id来判断是否已经处理过了。
                old_id = -1
                if 'head_file_upload_id' in st.session_state:
                    old_id = st.session_state.head_file_upload_id
                if old_id != file_upload.id:
                    st.session_state.head_file_upload_id = file_upload.id
                    head_file = self.head_dir / file_upload.name
                    # print(head_file)
                    head_file.write_bytes(file_upload.getvalue())
                    cur.head = file_upload.name

            head_file = self.head_dir / cur.head
            if head_file.exists():
                image = Image.open(head_file)
                st.image(image, caption="头像")
            else:
                print(f"head image {head_file} don't exists")

            bot = sp.pydantic_form(key=cur.id, model=cur, submit_label=intent)
            if bot:
                self.save_bot(bot, is_update)
                if not is_update:
                    st.experimental_rerun()
            enc = tiktoken.encoding_for_model(cur.model.value)
            tokens = enc.encode(cur.prompt)
            st.text(f"token总数:{len(tokens)}")

            

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
    parser = argparse.ArgumentParser()
    parser.add_argument('--config_dir', type=Path, default=Path("../configdir"))
    parser.add_argument('--head_dir', type=Path, default=Path("../configdir/head"))
    args = parser.parse_args(sys.argv[1:])
    editor = BotConfigEditor(args.config_dir, args.head_dir)
    editor.show()
