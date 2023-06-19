<template>
  <div style="margin-top: g">
    <label class="title-text">
      Lista usług
      <br>
      <br>
      <router-link v-if="userRole === 'user' && canAdd === 'true'" to="/add-service">Dodaj usługę</router-link>
      <form @submit.prevent="filter">

        <select id="name" v-model="selector" placeholder="Wybierz filtr" required>
          <option value="Wszystkie">Wszystkie</option>
          <option value="Zabiegi kosmetyczne">Zabiegi kosmetyczne</option>
          <option value="Fryzjer">Fryzjer</option>
          <option value="Fizjoterapeuta">Fizjoterapeuta</option>
          <option value="Psycholog">Psycholog</option>
          <option value="Trener personalny">Trener personalny</option>
          <option value="Masazysta">Masazysta</option>
        </select>
        <button type="submit" class="add-button">Filtruj</button>
      </form>
      

    </label>
    <table class="table table-image">
      <thead>
        <tr>
          <th scope="col">name</th>
          <th scope="col">user</th>
          <th scope="col">price</th>
          <th scope="col">photo</th>
          <th scope="col">rating</th>
          <th scope="col">details</th>
          <th scope="col" >Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="service in bookList" v-bind:key="service.id">
          <td>{{ service.name }}</td>
          <td>{{ service.user.username }}</td>
          <td>{{ service.price }}</td>
          <td class="w-25">
            <img v-if="service.name==='Zabiegi kosmetyczne'" src="https://carpediem-klinika.pl/wp-content/uploads/2017/03/carpediem-zabiegi-kosmetyczne.jpg"
              class="img-fluid img-thumbnail" alt="Sheep">
              <img v-if="service.name==='Fryzjer'"  src="https://zsm.resman.pl/wp-content/uploads/2022/03/fryzjer.jpg" class="img-fluid img-thumbnail"
              alt="Sheep">
              <img v-if="service.name==='Fizjoterapeuta'" src="https://centrum.fizjoterapeuty.pl/wp-content/uploads/2022/03/Pierwsza-w.jpg"
              class="img-fluid img-thumbnail" alt="Sheep">
              <img v-if="service.name==='Psycholog'" src="https://lekarzezradosci.pl/wp-content/uploads/2020/06/mezczyzna-na-rozmowie-u-psychologa.jpg"
                    class="img-fluid img-thumbnail" alt="Sheep">
              <img v-if="service.name==='Trener personalny'"
                    src="https://cdn.galleries.smcloud.net/t/galleries/gf-yDqg-CtwF-ZQ7h_trener-personalny-pomaga-sportsmence-wykonywac-cwiczenia-ze-sztanga-na-silowni-664x442.jpg"
                    class="img-fluid img-thumbnail" alt="Sheep">
              <img v-if="service.name==='Masazysta'" src="https://badzzdrowy.pl/wp-content/uploads/2019/06/masaz-nuru.jpg"
                    class="img-fluid img-thumbnail" alt="Sheep">
          </td>
          <td>
            <img  src="data:image/png;base64,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" >
          </td>

          <td>
            <button id="edit" type="button" class="btn btn-warning" @click="serviceDetails(service.id)">Details
            </button>
          </td>

          <td v-if="userRole === 'admin'">
            <button id="edit" type="button" class="btn btn-warning" @click="editService(service.id)">Edit
            </button>
          </td>
          <td v-if="userRole === 'admin'">
            <button id="delete" type="button" class="btn btn-danger" @click="deleteService(service.id)">Delete</button>
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
      selector: "",
      choosenSelector: this.$route.query.id,
      userRole: localStorage.getItem('userRole'),
      canAdd: localStorage.getItem('canAddService')
    }
  },
  methods: {
    getBookList() {
      ServiceService.getList().then((response) => {
        this.bookList = response.data;
      })
    },
    async editService(id) {
      this.$router.push({ path: '/edit-service', query: { id: id } });
    },

    async filter(){
      ServiceService.getListFilter(this.selector).then((response) => {
        this.bookList = response.data;
      })
    },

    
    async serviceDetails(id) {
      this.$router.push({ path: '/detail-service', query: { id: id } });
    },
    async rentBook(id) {
      var sessionId = localStorage.getItem('token')
      try {
        var response = await fetch(`http://localhost:8081/service/${id}/rent/${sessionId}`, {
          method: "POST",
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

    async returnBook(id) {
      var sessionId = localStorage.getItem('token')
      try {
        var response = await fetch(`http://localhost:8081/service/${id}/return/${sessionId}`, {
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
    async deleteService(id) {
      try {
        var response = await fetch(`http://localhost:8081/service/${id}`, {
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
    }

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