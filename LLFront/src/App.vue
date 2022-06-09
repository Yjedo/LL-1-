<template>
  <div id="app">
    <h1>LL(1)文法分析器</h1>
    <LL1></LL1>
  </div>
</template>

<script>
import LL1 from './components/LL1.vue'

export default {
  name: 'App',
  components: {
    LL1,
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
body{
  background: paleturquoise;
}
</style>
