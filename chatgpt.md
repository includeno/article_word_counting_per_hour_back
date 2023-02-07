

# 通用问题



## 请根据下面的SQL，写出对应的mybatis-plus实体类

```

```



## 请根据下面的接口，使用vue2和ant-design-pro-vue写出对应的页面

```
@GetMapping("/article/{articleId}/versions")
    public JsonResult<IPage<ArticleVersion>> getArticleVersions(@PathVariable Long articleId,
                                                                @RequestParam(defaultValue = "1") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size) {
        Page<ArticleVersion> articleVersionPage = new Page<>(page, size);
        IPage<ArticleVersion> articleVersionVOPage = articleLogicService.getArticleVersions(articleId, articleVersionPage);
        return JsonResult.ok(articleVersionVOPage);
    }
```



## 根据下面的格式，将Axios的api代码单独写在一个文件里

```
import {BASE_URL} from './Base'
import axios from "axios";

/*
* 定义路径
* */
let USER = BASE_URL + "/user";
let LOGIN = BASE_URL + "/user/login";
let REGISTER = BASE_URL + "/user/register";
let ROLES = BASE_URL + "/roles";
let USER_LIST = BASE_URL + "/users/page";
let ROUTES = BASE_URL + "/routes";

/**
 * 登录服务
 * @param email 账户名
 * @param password 账户密码
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function login(email, password) {
    let formdata = new FormData();
    formdata.append("email", email);
    formdata.append("password", password);
    return await axios.post(LOGIN, formdata);
}
```



理解一下下面的standard-table组件，并重写一下带有查看版本工具栏的表格

```
<template>
  <div class="standard-table">
    <div class="alert">
      <a-alert type="info" :show-icon="true" v-if="selectedRows">
        <div class="message" slot="message">
          已选择&nbsp;<a>{{selectedRows.length}}</a>&nbsp;项 <a class="clear" @click="onClear">清空</a>
          <template  v-for="(item, index) in needTotalList" >
            <div v-if="item.needTotal" :key="index">
              {{item.title}}总计&nbsp;
              <a>{{item.customRender ? item.customRender(item.total) : item.total}}</a>
            </div>
          </template>
        </div>
      </a-alert>
    </div>
    <a-table
      :bordered="bordered"
      :loading="loading"
      :columns="columns"
      :dataSource="dataSource"
      :rowKey="rowKey"
      :pagination="pagination"
      :expandedRowKeys="expandedRowKeys"
      :expandedRowRender="expandedRowRender"
      @change="onChange"
      :rowSelection="selectedRows ? {selectedRowKeys, onSelect, onSelectAll} : undefined"
    >
      <template slot-scope="text, record, index" :slot="slot" v-for="slot in Object.keys($scopedSlots).filter(key => key !== 'expandedRowRender') ">
        <slot :name="slot" v-bind="{text, record, index}"></slot>
      </template>
      <template :slot="slot" v-for="slot in Object.keys($slots)">
        <slot :name="slot"></slot>
      </template>
      <template slot-scope="record, index, indent, expanded" :slot="$scopedSlots.expandedRowRender ? 'expandedRowRender' : ''">
        <slot v-bind="{record, index, indent, expanded}" :name="$scopedSlots.expandedRowRender ? 'expandedRowRender' : ''"></slot>
      </template>
    </a-table>
  </div>
</template>

<script>
export default {
  name: 'StandardTable',
  props: {
    bordered: Boolean,
    loading: [Boolean, Object],
    columns: Array,
    dataSource: Array,
    rowKey: {
      type: [String, Function],
      default: 'key'
    },
    pagination: {
      type: [Object, Boolean],
      default: true
    },
    selectedRows: Array,
    expandedRowKeys: Array,
    expandedRowRender: Function
  },
  data () {
    return {
      needTotalList: []
    }
  },
  methods: {
    equals(record1, record2) {
      if (record1 === record2) {
        return true
      }
      const {rowKey} = this
      if (rowKey && typeof rowKey === 'string') {
        return record1[rowKey] === record2[rowKey]
      } else if (rowKey && typeof rowKey === 'function') {
        return rowKey(record1) === rowKey(record2)
      }
      return false
    },
    contains(arr, item) {
      if (!arr || arr.length === 0) {
        return false
      }
      const {equals} = this
      for (let i = 0; i < arr.length; i++) {
        if (equals(arr[i], item)) {
          return true
        }
      }
      return false
    },
    onSelectAll(selected, rows) {
      const {getKey, contains} = this
      const unselected = this.dataSource.filter(item => !contains(rows, item, this.rowKey))
      const _selectedRows = this.selectedRows.filter(item => !contains(unselected, item, this.rowKey))
      const set = {}
      _selectedRows.forEach(item => set[getKey(item)] = item)
      rows.forEach(item => set[getKey(item)] = item)
      const _rows = Object.values(set)
      this.$emit('update:selectedRows', _rows)
      this.$emit('selectedRowChange', _rows.map(item => getKey(item)), _rows)
    },
    getKey(record) {
      const {rowKey} = this
      if (!rowKey || !record) {
        return undefined
      }
      if (typeof rowKey === 'string') {
        return record[rowKey]
      } else {
        return rowKey(record)
      }
    },
    onSelect(record, selected) {
      const {equals, selectedRows, getKey} = this
      const _selectedRows = selected ? [...selectedRows, record] : selectedRows.filter(row => !equals(row, record))
      this.$emit('update:selectedRows', _selectedRows)
      this.$emit('selectedRowChange', _selectedRows.map(item => getKey(item)), _selectedRows)
    },
    initTotalList (columns) {
      return columns.filter(item => item.needTotal)
        .map(item => {
          return {
            ...item,
            total: 0
          }
        })
    },
    onClear() {
      this.$emit('update:selectedRows', [])
      this.$emit('selectedRowChange', [], [])
      this.$emit('clear')
    },
    onChange(pagination, filters, sorter, {currentDataSource}) {
      this.$emit('change', pagination, filters, sorter, {currentDataSource})
    }
  },
  created () {
    this.needTotalList = this.initTotalList(this.columns)
  },
  watch: {
    selectedRows (selectedRows) {
      this.needTotalList = this.needTotalList.map(item => {
        return {
          ...item,
          total: selectedRows.reduce((sum, val) => {
            let v
            try{
              v = val[item.dataIndex] ? val[item.dataIndex] : eval(`val.${item.dataIndex}`);
            }catch(_){
              v = val[item.dataIndex];
            }
            v = !isNaN(parseFloat(v)) ? parseFloat(v) : 0;
            return sum + v
          }, 0)
        }
      })
    }
  },
  computed: {
    selectedRowKeys() {
      return this.selectedRows.map(record => this.getKey(record))
    },
  }
}
</script>

<style scoped lang="less">
.standard-table{
  .alert{
    margin-bottom: 16px;
    .message{
      a{
        font-weight: 600;
      }
    }
    .clear{
      float: right;
    }
  }
}
</style>

```



## 使用StandardTable 组件写出来适配下面接口的页面：

```

```

