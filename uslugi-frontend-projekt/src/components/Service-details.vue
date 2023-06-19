<template>
  <div class="containerDisplay">
    <form @submit.prevent="rentBook(id,service.isAbleToUse)">
      <br>
      <br>
      <h3>Dane usługi</h3>
      <div class="form-group">
        <div class="display-error-login" v-if="displayIncorrectPassword"> Niepoprawna nazwa użytkownika lub hasło </div>
        <div class="display-error-login" v-if="displayServerError"> Wystąpił problem z serwerem</div>
        <br>
        <h2>Name</h2>
        {{ service.name }}
        <br>
        
        <h2>Price</h2>
        {{ service.price }}
        <br>

        <h2>Description</h2>
        {{ service.description }}
        <br>
        <br>
        <h2>Choose a time for your appointment:</h2>
      <input type="date" id="meeting-time" v-model="date" name="meeting-time">
      </div>
      <button type="submit" class="add-button">Akcja</button>
      <button v-if="loginStatus" type="submit" class="add-button">Dodawanie...</button>
    </form>
  </div>
</template>
  
<script>
import ServiceService from '../services/ServiceService'

export default {

  data() {
    return {
      id: this.$route.query.id,
      price: "",
      name: "",
      email: "",
      date: "",
      bookList: [],
      showPassword: false,
      displayIncorrectPassword: false,
      loginStatus: false,
      displayServerError: false,
      sessionId: localStorage.getItem('token'),
      service: ""
    }
  },
  computed: {
    buttonLabel() {
      return (this.password) ? "Hide" : "Show";
    }
  },
  methods: {
    getBookList() {
      ServiceService.getHistoryList().then((response) => {
        this.bookList = response.data;
      })
    },
    getService() {
      var id = this.$route.query.id;
      ServiceService.getService(id).then((response) => {
        this.service = response.data;
      })
    },

    async rentBook(id) {
      var sessionId = localStorage.getItem('token')

      var canRent = true;
      for (let i = 0; i < this.bookList.length; i++) {
          if(this.bookList[i].rentalDate === this.date){
            canRent = false;
          }
    }
    var canReturn = false;
    for (let i = 0; i < this.bookList.length; i++) {
          if(this.bookList[i].rentalDate === this.date && this.bookList[i].service.user.sessionId === sessionId){
            canReturn = true;
          }
    }

    if(canRent === false && canReturn === false){
      console.log("TEN TERMIN JEST JUZ ZAJETY")
    }
    else{
      if(canRent === true){
      try {
        var response = await fetch(`http://localhost:8081/service/${id}/rent/${sessionId}`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "*",
            "Access-Control-Allow-Headers": "*"
          },
          body: JSON.stringify({
            date: this.date
          })
        }).then(response => response.json())
      }
      catch (error) {
        console.log(error)
      }
      console.log(response)
    }
    else{
      try {
        response = await fetch(`http://localhost:8081/service/${id}/return/${sessionId}`, {
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
    }
      this.$router.push('/service-list')
    }
    },


    markCheckbox() {
      document.getElementById("checkboxButton").click()
    },

    toggleShow() {
      this.showPassword = !this.showPassword;
    }
  },
  created: function () {
    this.getService()
    this.getBookList();
  }


}
</script>
  
<style>
.checkboxClass {
  width: 5%;
  height: 5%;
}

.showPasswordClass {
  padding-top: 1%;
}

.containerDisplay {
  text-align: center;
  align-items: center;
  justify-content: center;
  display: inline-block;
  float: none;
  width: 100%;

}

.form-control-lg {
  display: block;
  width: 30%;
  height: calc(1.5em + 0.75rem + 2px);
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  display: inline-block;
  transition: border-color .15s ease-in-out, box-shadow .15s ease-in-out;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #62478a;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

#toggle-password {
  display: none;
}

#toggle-password+label {
  text-indent: -9999px;
  display: inline-block;
  width: 20px;
  height: 20px;
  margin-left: -32px;
  cursor: pointer;
}

button {
  width: 30%;
  justify-content: center;
}

label {
  margin-left: +10px;
}

.display-error-login {
  color: #ff0000;
  padding: 10px;
  margin: auto;
  border-radius: 10px;
  text-align: center;
}

div #showPasswordLabel:hover,
button #checkboxButton:hover {
  cursor: pointer;
}

.redirectToLogin {
  float: right;
  padding-right: 5%;
}

.redirectToLogin:hover {
  cursor: pointer;
  text-decoration: underline;
}</style>