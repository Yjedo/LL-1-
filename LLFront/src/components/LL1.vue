<template>
  <div class="ll1">
    <div class="str">
      <el-input v-model="str" placeholder="请输入待分析字符串"></el-input>
    </div>
    <div class="gs">
      <el-input type="textarea" :rows="4" placeholder="请输入文法" v-model="GS"> </el-input>
    </div>
    <div>
      <el-button type="primary" @click="getData">开始分析</el-button>
    </div>
    <div class="show">
      <div class="showFirst">
        <p>first集合</p>
        <p v-for="item, i in first" :key="i">
          {{item}}
        </p>
      </div>
      <div class="showFollow">
        <p>follow集合</p>
         <p v-for="item, i in follow" :key="i">
          {{item}}
        </p>
      </div>
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
        label="剩余字符串">
      </el-table-column>
       <el-table-column
        prop="s"
        label="产生式">
      </el-table-column>
       <el-table-column
        prop="action"
        label="操作行为">
      </el-table-column>
    </el-table>
    </div>
    <div class="analyzeTable">
      <p>分析表</p>
      <ul v-for="item, i in M" :key="i">
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
  name: 'LL1',
  props: {
    msg: String,
  },
 
  data() {
    return {
      str: '',
      GS: '',
      first: [],
      follow: [],
      M: [],
      data:[],
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄',
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄',
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄',
        },
      ],
    }
  },

   methods: {
    getData() {
      alert("开始发送")
      let param = {
        str: this.str,
        GS: this.GS
      }
      let that = this
      request({
        url: '/analyze',
        method: 'get',
        params:param,
      }).then((response)=>{
        that.first = response.data.first
        that.follow = response.data.follow
        that.M = response.data.M
        that.data =  response.data.data
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
  background: palevioletred;
  line-height: 30px;
  margin: 5px;
}

.ll1 {
  background: white;
  width: 800px;
  height: 1800px;
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
