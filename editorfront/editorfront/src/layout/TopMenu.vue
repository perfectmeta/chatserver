<script setup lang="ts">
import { ref } from 'vue'
// import { ElMenu, ElMenuItem } from 'element3';

function handleSelect(event: any) {
    console.log(event.target)
}

let activeIndex = ref<string>('1');

</script>
<template>
    <header>    
        <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect">
            <el-menu-item index="1">First Menu Item</el-menu-item>
            <el-menu-item index="2">First Menu Item 2</el-menu-item>
            <el-menu-item index="3">First Menu Item 3</el-menu-item>
        </el-menu>
    </header>
</template>

<style>

</style>