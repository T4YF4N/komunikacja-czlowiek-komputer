<template>
	<div style="margin-top: g">
		<label class="title-text"> Historia rezerwacji </label>
		<table class="table table-image">
			<thead>
				<tr>
					<th scope="col">Id</th>
					<th scope="col">Nazwa</th>
					<th scope="col">Klient</th>
					<th scope="col">Usługodawca</th>
					<th scope="col">rentalDate</th>
					<th scope="col">returnDate</th>
					<th scope="col">Image</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="rental in bookList" v-bind:key="rental.id">
					<td>{{ rental.id }}</td>
					<td>{{ rental.service.name }}</td>
					<td>{{ rental.user.username }}</td>
					<td>{{ rental.service.user.username }}</td>
					<td>{{ rental.rentalDate }}</td>
					<td>{{ rental.returnDate }}</td>
					<td class="w-25">
						<img
							src="https://www.tauron.pl/dla-domu/prad/old/-/media/additional/schulz/img/produktowe/usluga-zdrowie-24h/1.jpg"
							class="img-fluid img-thumbnail"
							alt="Sheep"
						/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</template>

<script>
import ServiceService from '../services/ServiceService';

export default {
	data() {
		return {
			bookList: [],
		};
	},
	methods: {
		getHistoryList() {
			var userRole = localStorage.getItem('userRole');
			var sessionId = localStorage.getItem('token');
			if (userRole === 'admin') {
				ServiceService.getHistoryList().then((response) => {
					this.bookList = response.data;
				});
			} else {
				ServiceService.getUserHistoryList(sessionId).then((response) => {
					this.bookList = response.data;
				});
			}
		},
	},
	created: function () {
		this.getHistoryList();
	},
};
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
