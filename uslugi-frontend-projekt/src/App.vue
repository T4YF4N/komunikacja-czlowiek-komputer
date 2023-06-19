<template>
  <div class="vue-template" @mouseover="updateLoginButton">
    <!-- Navigation -->
    <nav class="navbar shadow bg-white rounded justify-content-between flex-nowrap flex-row fixed-top">
      <div class="container">
        <a class="navbar-brand float-left" target="_blank">
          <router-link to="/main">
            <img src="./img/logo.jpg" width="100" heigh="100" />
          </router-link>
        </a>
        <ul class="nav-leftside">
          <li class="nav-items">
            <router-link v-if="showLogoutButton" to="/service-list">Lista usług</router-link>
          </li>
        </ul>
        <ul class="nav-rightside">
          <li class="nav-items">
            <button style="width:100%" v-if="showLogoutButton && userRole === 'user'" id="editUser" type="button" class="btn btn-warning"
              @click="editUser()">Konto usera</button>
          </li>
        </ul>
        <ul class="nav-rightside">
          <li class="nav-items">
            <router-link v-if="showLogoutButton && userRole === 'admin'" to="/user-list">Lista użytkowników</router-link>
          </li>
        </ul>
        <ul class="nav-rightside">
          <li v-if="showLogoutButton" class="nav-items">
            <span class="btn-circle" v-if="displayNumberOfItems"> {{ numberOfItemsCart }} </span>
            <router-link to="/check-out">Historia usług</router-link>
          </li>
          <li class="nav-items">
            <router-link v-if="showLogoutButton" to="/log-in" @click="clearSessionStorage">Wyloguj</router-link>
          </li>
          <li class="nav-items">
            <router-link v-if="!showLogoutButton" to="/log-in">Zaloguj</router-link>
          </li>
        </ul>
      </div>
    </nav>
    <!-- Main -->
    <div class="App" @mouseover="updateCartItemsNumber()" @click="updateCartItemsNumber()">
      <div class="vertical-center">
        <img src="./img/uslugi.jpg" />
        <div class="inner-block">
          <router-view />
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {

  data() {
    return {
      showLogoutButton: localStorage.getItem('token'),
      userRole: localStorage.getItem('userRole'),
      showSignUpButton: false,
      numberOfItemsCart: 0,
      displayNumberOfItems: false,
    }
  },
  methods: {
    async editUser() {
      var id = localStorage.getItem('id')
      this.$router.push({ path: '/edit-user', query: { id: id } });
    },

    updateCartItemsNumber() {
      let tempItemsNumber = 0
      if (!isNaN(parseInt(localStorage.getItem('numberOfRoomsAdded'), 10))) {
        tempItemsNumber += parseInt(localStorage.getItem('numberOfRoomsAdded'), 10)
      }
      if (!isNaN(parseInt(localStorage.getItem('numberOfTreatmentsAdded'), 10))) {
        tempItemsNumber += parseInt(localStorage.getItem('numberOfTreatmentsAdded'), 10)
      }

      if (tempItemsNumber > 0) {
        this.numberOfItemsCart = tempItemsNumber
        this.displayNumberOfItems = true
      }
      else
        this.displayNumberOfItems = false
    },
    updateLoginButton() {
      if (localStorage.getItem('token') === null) {
        this.showLogoutButton = false
        this.showSignUpButton = true
      }
    },
    clearSessionStorage() {
      localStorage.clear();
      this.$router
        .push({ path: '/log-in' })
        .then(() => { this.$router.go() })
    }
  },
  mounted: function () {
    this.updateLoginButton()
  },
}



</script>


<style>
.btn-circle {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  border-top-left-radius: 50%;
  border-top-right-radius: 50%;
  border-bottom-right-radius: 50%;
  border-bottom-left-radius: 50%;
  position: absolute;
  right: 327px;
  background-color: orange;
  color: white;
  display: -ms-flexbox;
  display: flex;
  -ms-flex-align: center;
  align-items: center;
  -ms-flex-pack: center;
  justify-content: center;
  font-size: 15px;
  border: 1px;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: black;
}

.vertical-center {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  overflow: scroll;
}

.inner-block {
  position: absolute;
  text-align: center;
  top: 35%;
  left: 50%;
  width: fit-content;
  min-width: 300px;
  margin-bottom: 30px;
  margin-left: 80px;
  transform: translate(-50%, -50%);
}

.nav-leftside {
  display: flex;
  float: left;
}

.nav-rightside {
  display: flex;
  float: right;
}

.nav-items {
  font-size: 20px;
  padding-left: 10%;
  padding-right: 10%;
  padding-top: 3%;
}
</style>