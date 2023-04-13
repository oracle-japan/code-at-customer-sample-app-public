<template>
  <div>
    <app-header />
    <input type="text" v-model="id" placeholder="IDでフィルタ" class="inputId">
    &nbsp;
    <button @click="getItems()">商品情報を取得</button>
    &nbsp;
    <button @click="clear()">クリア</button>
    <p class="error">{{ inputError }}</p>
    <p>
      {{ msg }}
    </p>
    <!-- div v-html="response_html"></div -->

    <div class="th-sticky_wrap"  v-if="items.length > 0">
			<table class="st-tbl1">
				<thead>
					<tr>
						<th>ID</th>
						<th>商品名</th>
						<th>価格(USD)</th>
            <th>ステータス</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(item) in items" v-bind:key="item.id">
						<td style="text-align: right;">{{ item.id}}</td>
						<td style="text-align: left;">{{ item.name }}</td>
						<td style="text-align: right;">{{ item.price }}</td>
						<td style="text-align: center;">{{ item.status }}</td>
					</tr>
				</tbody>
			</table>
		</div>

  </div>
</template>

<script>
import AppHeader from './components/AppHeader'

export default {
  components: { AppHeader },
  data() {
    return {
      id: '',
      inputError: '',
      msg: '',
      items: []
    }
  },
  methods: {
    getItems() {
      this.inputError = ''
      this.msg = ''
      this.items = []
      if(! this.isInValidId()){
        this.inputError = '入力されたIDが不正です.'
        return
      }
      fetch('/items/' + this.id)
        .then(response => {
          if(!response.ok) {
            response.text().then(txt => { 
              console.log(txt)
              this.items = []
              if(null != txt && txt.length > 0){
                this.msg = txt
              }else{
                this.msg = `Error: ${response.status} ${response.statusText}`
              }
              //throw new Error(`${response.status} ${response.statusText} ${txt}`);
            })
            return []
          }else{
            return response.json()
          }
        })
        .then(json => {
          this.msg = ''
          if(Array.isArray(json)){
            this.items = json;
          }else{
            this.items = [json];
          }
        })
        .catch((err) => {
          console.log(err)
          this.msg = err;
        });
    },
    clear() {
      this.id = '';
      this.inputError = '';
      this.msg = '';
      this.items = [];
    },
    isInValidId() {
      return 0 == this.id.length || ((! isNaN(this.id)) && (parseInt(this.id) > 0))
    }
  }
}
</script>

<style>
#app, pre {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

th,td {
  border: solid 1px;
  padding: 5px 10px;
}

th {
  background-color:  #ddd;
  font-weight:  normal;
}

table {
  border-collapse:  collapse;
  margin: auto;
}

input.inputId { 
  width: 100px; 
}

p.error {
  color: #ff0000;
  font-size: small;
}

</style>
