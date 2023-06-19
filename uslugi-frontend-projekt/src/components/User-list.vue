<template>
  <div style="margin-top: g">
    <label class="title-text">
      Lista użytkowników
      <br>
      <br>
      <router-link v-if="userRole === 'user' && canAdd === true" to="/add-service">Dodaj usługę</router-link>
    </label>
    <table class="table table-image">
      <thead>
        <tr>
          <th scope="col">Id</th>
          <th scope="col">username</th>
          <th scope="col">email</th>
          <th scope="col">userRole</th>
          <th scope="col">canAddService</th>
          <th scope="col">Edit user</th>
          <th scope="col">Delete user</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in bookList" v-bind:key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.userRole }}</td>
          <td>{{ user.canAddService }}</td>
          <td>
            <button id="editUser" type="button" class="btn btn-warning" @click="editUser(user.id)">Edit</button>
          </td>
          <td>
            <button id="rentBook" type="button" class="btn btn-danger" @click="deleteUser(user.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import ServiceService from '../services/ServiceService'


export default {
  data() {
    return {
      bookList: [],
      userRole: localStorage.getItem('userRole'),
      canAdd: localStorage.getItem('canAddService')
    }
  },
  methods: {
    getBookList() {
      ServiceService.getUserList().then((response) => {
        this.bookList = response.data;
      })
    },
    async editUser(id) {
      this.$router.push({ path: '/edit-user', query: { id: id } });
    },

    async deleteUser(id) {
      try {
        var response = await fetch(`http://localhost:8081/delete/${id}`, {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "*",
            "Access-Control-Allow-Headers": "*"
          }
        }).then(response => response.json())
      }
      catch (error) {
        console.log(error)
      }
      console.log(response)
      location.reload();
    },

  },
  created: function () {
    console.log('TESTTT')
    this.getBookList()
    console.log('TESTTT111')
  },
}

</script>

<style>
img {
  border-radius: 0.25rem;
}

.btn {
  width: 50%;
}

h2 {
  display: block;
  font-size: 1.5em;
  margin-block-start: 0.83em;
  margin-block-end: 0.83em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  font-weight: bold;
}

.price {
  width: 100%;
  -ms-flex-item-align: start;
  align-self: flex-start;

  -ms-flex-pack: justify;
  justify-content: space-between;
  margin-bottom: 0.5em;
}

.listOfProducts {
  width: 100%;
  max-width: 1000px;
  margin: 0 auto;
  display: -ms-flexbox;
  display: flex;
  -ms-flex-wrap: wrap;
  flex-wrap: wrap;
  -ms-flex-pack: distribute;
  justify-content: space-around;
  padding: 0;
}

.product {
  width: 300px;
  background-color: #fff;
  list-style: none;
  box-sizing: border-box;
  padding: 1em;
  margin: 1em 0;
  display: -ms-flexbox;
  display: flex;
  display: inline-block;
  -ms-flex-direction: column;
  flex-direction: column;
  -ms-flex-align: center;
  align-items: center;
  border-radius: 7px;
}

.title-text {
  font-size: 25px;
  padding-bottom: 3%;
}

h1 {
  font-size: 15px;
}

ul {
  list-style-type: none;
  font-weight: bold;
  font-size: 20px;
}

label {
  margin-left: +10px;
}

.container {
  padding: 2rem 0rem;
}

h4 {
  margin: 2rem 0rem 1rem;
}

.table-image {

  td,
  th {
    vertical-align: middle;
  }
}
</style>