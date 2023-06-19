export function speichereVorlageStorage(password: string) {
  localStorage.setItem('password', JSON.stringify(password));
  }



  export function ladeVorlageStorage(): string {
    if (sessionStorage.getItem('password')) {
      return JSON.parse(localStorage.getItem('password'));
    }
  }