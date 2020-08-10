<template>
  <q-page class="q-pa-md">
    <q-table
      title="Workspaces"
      :data="data"
      :columns="columns"
      row-key="name"
      :loading="loading"
      :filter="filter"
      @request="onRequest"
      :pagination="initialPagination"
      >
      <template v-slot:top-right>
        <q-input borderless dense debounce="300" v-model="filter" placeholder="Search">
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>
        <q-btn style="margin-left: 3em;" color="primary" :disable="loading" label="Add Workspace" @click="addWorkspace" />
      </template>
      <template v-slot:body-cell-name="props">
        <q-td :props="props">
          <div>
            {{ props.row.displayPath }}
          </div>
        </q-td>
      </template>
      <template v-slot:body-cell-running="props">
        <q-td :props="props">
          <div>
            {{ props.row.running }}
          </div>
        </q-td>
      </template>
    </q-table>
  </q-page>
</template>

<script>
const axios = require('axios');

export default {
  data () {
    return {
      filter: '',
      loading: false,
      initialPagination: {
        rowsPerPage: 10
      },
      columns: [
        {
          name: 'name',
          required: true,
          label: 'Path',
          align: 'left',
          field: row => row.displayPath,
          format: val => `${val}`,
          sortable: true,
          style: 'width: 75%'
        },
        {
          name: 'running',
          align: 'center',
          label: 'Running',
          field: 'running',
          sortable: true,
          style: 'max-width: 3em',
        },
        {
          name: 'sessions',
          align: 'center',
          label: 'Sessions',
          field: 'sessions',
          sortable: true,
          style: 'max-width: 100px',
        }
      ],
      data: []
    }
  },
  mounted () {
    // get initial data from server (1st page)
    this.onRequest({})
  },
  methods: {
    onRequest (props) {
      console.log('xxxxxxx', props);

      this.loading = true;

      axios('http://localhost:8880/jred-console/manager/list')
        .then(response => {
          console.log(response.data);
          this.data = response.data;
        })
        .catch(error => console.log('Error',  error.message));

      // emulate server
      setTimeout(() => {

        // ...and turn of loading indicator
        this.loading = false
      }, 1500);
    },
    addWorkspace () {
      alert('xxx')
    }
  }
}
</script>
