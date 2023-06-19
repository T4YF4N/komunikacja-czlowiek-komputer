import axios from 'axios'

class ServiceService {

    async getList() {
        console.log('HEREEE')
        var response =await axios.get("http://localhost:8081/services");
        console.log(response)
        return response;
    }

    getUserList() {
        console.log('HEREEE')
        var response = axios.get("http://localhost:8081/getall");
        console.log(response)
        return response;
    }

    getUser(id) {
        var response = axios.get("http://localhost:8081/get/" + id);
        return response;
    }

    getService(id) {
        var response = axios.get("http://localhost:8081/service/" + id);
        return response;
    }

    getHistoryList() {
        var response = axios.get('http://localhost:8081/users/rentals');
        return response;
    }

    getUserHistoryList(sessionId) {
        var response = axios.get('http://localhost:8081/users/' + sessionId + '/rentals');
        console.log(response)
        return response;
    }

    getListFilter(selector){
        var response = axios.get('http://localhost:8081/services/' + selector);
        console.log(response)
        return response;
    }

}
export default new ServiceService()