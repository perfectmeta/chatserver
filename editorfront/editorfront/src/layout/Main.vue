
<template>
    <el-container>
        <el-header>
            <!-- <TopMenu></TopMenu> -->
        </el-header>
      <el-container>
        <el-aside>
            <LeftSide @robot-change="onRobotChange" @skill-change="onSkillChange" :data="{curRobot, curSkill}"/>
        </el-aside>
        <el-container style="width: 100%; height: 100%;">
            <el-main robot=curRobot skill=curSkill>
                <ContentView></ContentView>
            </el-main>
        </el-container>
      </el-container>
    </el-container>
</template>


<script setup lang="ts">

// import { ElContainer, ElHeader, ElAside, ElMain } from 'element3';
import { Ref, ref } from 'vue'
import LeftSide from './LeftSide.vue'
import ContentView from './ContentView.vue'
// import { store } from '../store/store.js'

let curRobot: Ref<any> = ref('') //store.curRobot
let curSkill: Ref<string> = ref('') // store.curSkill


function onRobotChange(robot: string) {
  // store.setCurRobot(robot)
  console.log(robot)
}

function onSkillChange(skill: string) {
  // store.setCurSkill(skill)
  console.log(skill)
}

</script>

<style>
  .el-header,
  .el-footer {
    background-color: #b3c0d1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #d3dce6;
    color: #333;
    text-align: center;
    line-height: 30px;
    height: 100vh;
  }

  .el-main {
    background-color: #e9eef3;
    color: #333;
    text-align: center;
    line-height: 160px;
    height: 100%;
  }

  body > .el-container {
    margin-bottom: 40px;
    width: 100%;
    height: 100vh;
  }

</style>../store/index.js