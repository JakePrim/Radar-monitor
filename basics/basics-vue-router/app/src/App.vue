<template>
  <div id="app">
    <home-header></home-header>
    <router-view/>
  </div>
</template>
<script>
// @ is an alias to /src
import HomeHeader from "@/components/HomeHeader";
export default {
  name:'App',
  components:{
    HomeHeader
  }
}
</script>
<style>
</style>
