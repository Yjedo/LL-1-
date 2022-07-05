<template>
  <div class="lr1">
     <h1>LR(1)文法分析器</h1>
    <div class="str">
      <el-input v-model="str" placeholder="请输入待分析字符串"></el-input>
    </div>
    <div class="gs">
      <el-input type="textarea" :rows="4" placeholder="请输入文法" v-model="GS"> </el-input>
    </div>
    <div>
      <el-button type="primary" @click="getData">开始分析</el-button>
    </div>
    <div class="result">
      <el-table
      :data="data"
      style="width: 100%">
      <el-table-column
        prop="step"
        label="步骤"
        width="180">
      </el-table-column>
      <el-table-column
        prop="stackData"
        label="栈数据"
        width="180">
      </el-table-column>
      <el-table-column
        prop="string"
        label="符号栈">
      </el-table-column>
       <el-table-column
        prop="s"
        label="剩余字符串">
      </el-table-column>
       <el-table-column
        prop="action"
        label="动作说明">
      </el-table-column>
    </el-table>
    </div>
    <div class="analyzeTable">
      <p>分析表</p>
      <ul v-for="item, i in table" :key="i">
        <li v-for="m, n in item" :key="n">
          {{m}}
        </li>
      </ul>
    </div>

  </div>
</template>

<script>
import request from '@/request'
export default {
  name: 'LLR',
  props: {
    msg: String,
  },
 
  data() {
    return {
      str: '',
      GS: '',
      data:[],
      table:[],
    }
  },

   methods: {
    getData() {
      alert("开始发送")
      let that = this
      request({
        url: '/lranalyze',
        method: 'get',
        params:{
          str: this.str,
          GS: this.GS
        }
      }).then((response)=>{
        that.data =  response.data.data
        that.table =  response.data.table
      })
    },
  },

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

ul{
  display: flex;
  justify-content: space-around;
  margin: 5px auto;
}


li{
  list-style: none;
  width: 60px;
  height: 30px;
  background: rgb(212, 142, 165);
  line-height: 30px;
  margin: 5px;
}

.lr1 {
  background: white;
  width: 800px;
  margin: 50px auto;
  overflow: hidden;
  border: 2px solid gray;
}

div {
  margin-bottom: 10px;
}

.gs {
  height: 100px;
}

.gs .el-input {
  height: 100%;
}

.show {
  height: 350px;
  display: flex;
  justify-content: space-around;
}

.showFirst {
  height: 320px;
  width: 300px;
  background: rgb(249, 248, 248);
   border: 1px solid gray;

}

.showFollow {
  height: 320px;
  width: 300px;
   border: 1px solid gray;

  background: rgb(249, 248, 248);
}

.analyzeTable{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>
