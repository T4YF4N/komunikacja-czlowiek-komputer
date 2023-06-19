<template>
  <div class="containerDisplay">
    <form @submit.prevent="login">
      <br>
      <br>
      <h3>Edytuj dane</h3>
      <div class="form-group">
        <div class="display-error-login" v-if="displayIncorrectPassword"> Niepoprawna nazwa użytkownika lub hasło </div>
        <div class="display-error-login" v-if="displayServerError"> Wystąpił problem z serwerem</div>
        <br>
        <input type="text" v-model="user.username" class="form-control-lg" required />
        <br>
        <br>
        <input type="text" v-model="user.email" class="form-control-lg" required />
        <br>
        <br>
        <p>Can add services</p>
        <input v-if="userRole==='admin'" type="checkbox" v-model="user.canAddService" class="form-control-lg" />
      </div>
      <button v-if="!loginStatus" type="submit" class="add-button">Edytuj</button>
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
      username: "",
      password: "",
      email: "",
      showPassword: false,
      displayIncorrectPassword: false,
      loginStatus: false,
      displayServerError: false,
      token: "",
      user: "",
      userRole: localStorage.getItem('userRole')
    }
  },
  computed: {
    buttonLabel() {
      return (this.password) ? "Hide" : "Show";
    }
  },
  methods: {
    getUser() {
      var id = this.$route.query.id;
      ServiceService.getUser(id).then((response) => {
        this.user = response.data;
      })
    },

    markCheckbox() {
      document.getElementById("checkboxButton").click()
    },

    toggleShow() {
      this.showPassword = !this.showPassword;
    },
    async login(e) {
      this.loginStatus = true
      e.preventDefault();

      try {
        var response = await fetch("http://localhost:8081/update/" + this.id, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "*",
            "Access-Control-Allow-Headers": "*"
          },
          body: JSON.stringify({
            username: this.user.username,
            password: "password",
            email: this.user.email,
            canAdd: this.user.canAddService
          })
        }).then(response => response.json())
      }
      catch (error) {
        this.errorMessage = error;
        console.error('There was an error!', error)
        const stringError = String(error)

        if (stringError.includes("Failed to fetch")) {
          this.displayServerError = true
          this.loginStatus = false
          this.displayIncorrectPassword = false
        }
        else {
          this.displayIncorrectPassword = true
          this.loginStatus = false
          this.displayServerError = false
        }
        return
      }

      if (response !== 'undefined') {
        var userRole = localStorage.getItem('userRole')
        if (userRole === 'admin') {
          this.$router.push('/user-list')
        }
        else {
          this.$router.push('/service-list')
        }
      }
    }
  },
  created: function () {
    this.getUser()
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