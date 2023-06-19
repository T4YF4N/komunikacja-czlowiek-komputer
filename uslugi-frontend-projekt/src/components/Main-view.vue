<template>
	<div id="app">
		<div class="main-page"></div>
		<label class="title-text"> Witamy na stronie usług </label>
		<h1>Usługi są to kluczowy element każdej gospodarki.</h1>

		<div class="container">
			<div class="row">
				<div class="col-12">
					<table class="table table-image">
						<thead>
							<tr>
								<th scope="col">Numer</th>
								<th scope="col">Zdjęcie</th>
								<th scope="col">Nazwa usługi</th>
								<th scope="col">Opcje</th>
								<th scope="col">Akcja</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="row">1</th>
								<td class="w-25">
									<img
										src="https://carpediem-klinika.pl/wp-content/uploads/2017/03/carpediem-zabiegi-kosmetyczne.jpg"
										class="img-fluid img-thumbnail"
										alt="Sheep"
									/>
								</td>
								<td>Zabiegi kosmetyczne</td>
								<td>{{ getNumberOfServices('Zabiegi kosmetyczne') }}</td>
								<td>
									<a href="http://localhost:8080/service-list">szczegóły</a>
								</td>
							</tr>
							<tr>
								<th scope="row">2</th>
								<td class="w-25">
									<img
										src="https://zsm.resman.pl/wp-content/uploads/2022/03/fryzjer.jpg"
										class="img-fluid img-thumbnail"
										alt="Sheep"
									/>
								</td>
								<td>Fryzjer</td>
								<td>{{ getNumberOfServices('Fryzjer') }}</td>
								<td>
									<a href="http://localhost:8080/service-list/Fryzjer"
										>szczegóły</a
									>
								</td>
							</tr>
							<tr>
								<th scope="row">3</th>
								<td class="w-25">
									<img
										src="https://centrum.fizjoterapeuty.pl/wp-content/uploads/2022/03/Pierwsza-w.jpg"
										class="img-fluid img-thumbnail"
										alt="Sheep"
									/>
								</td>
								<td>Fizjoterapeuta</td>
								<td>{{ getNumberOfServices('Fizjoterapeuta') }}</td>
								<td>
									<a href="http://localhost:8080/service-list/Fizjoterapeuta"
										>szczegóły</a
									>
								</td>
							</tr>
							<tr>
								<th scope="row">4</th>
								<td class="w-25">
									<img
										src="https://lekarzezradosci.pl/wp-content/uploads/2020/06/mezczyzna-na-rozmowie-u-psychologa.jpg"
										class="img-fluid img-thumbnail"
										alt="Sheep"
									/>
								</td>
								<td>Psycholog</td>
								<td>{{ getNumberOfServices('Psycholog') }}</td>
								<td>
									<a href="http://localhost:8080/service-list/Psycholog"
										>szczegóły</a
									>
								</td>
							</tr>
							<tr>
								<th scope="row">5</th>
								<td class="w-25">
									<img
										src="https://cdn.galleries.smcloud.net/t/galleries/gf-yDqg-CtwF-ZQ7h_trener-personalny-pomaga-sportsmence-wykonywac-cwiczenia-ze-sztanga-na-silowni-664x442.jpg"
										class="img-fluid img-thumbnail"
										alt="Sheep"
									/>
								</td>
								<td>Trener personalny</td>
								<td>{{ getNumberOfServices('Trener_personalny') }}</td>
								<td>
									<a href="http://localhost:8080/service-list/Trener_personalny"
										>szczegóły</a
									>
								</td>
							</tr>
							<tr>
								<th scope="row">6</th>
								<td class="w-25">
									<img
										src="https://badzzdrowy.pl/wp-content/uploads/2019/06/masaz-nuru.jpg"
										class="img-fluid img-thumbnail"
										alt="Sheep"
									/>
								</td>
								<td>Masażysta</td>
								<td>{{ getNumberOfServices('Masazysta') }}</td>
								<td>
									<a href="http://localhost:8080/service-list/Masazysta"
										>szczegóły</a
									>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<br />
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
		getBookList() {
			ServiceService.getList().then((response) => {
				this.bookList = response.data;
			});
		},
		getNumberOfServices(serviceName) {
			let count = 0;
			for (let i = 0; i < this.bookList.length; i++) {
				console.log(serviceName);
				console.log(this.bookList);
				if (this.bookList[i].name === serviceName) {
					count++;
				}
				return count;
			}
		},
	},
	created: function () {
		this.getBookList();
	},
};
</script>

<style>
.main-page {
	top: 30% !important;
}

.title-text {
	font-size: 25px;
	padding-bottom: 3%;
}

h1 {
	font-size: 15px;
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
