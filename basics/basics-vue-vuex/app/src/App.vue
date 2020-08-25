<template>
  <div id="app">
    <home-header></home-header>
    <transition name="fade">
      <router-view />
    </transition>
  </div>
</template>
<script>
// @ is an alias to /src
import HomeHeader from "@/components/HomeHeader";
export default {
  name: "App",
  components: {
    HomeHeader,
  },
};
</script>
<style>
.fade-enter-active {
  transition: opacity 0.5s;
}
.fade-leave-active {
  transition: none;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
