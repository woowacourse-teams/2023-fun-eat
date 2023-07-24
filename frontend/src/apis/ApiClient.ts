import { fetchApi } from './fetch';

export class ApiClient {
  #path: string;

  #headers: HeadersInit;

  constructor(path: string, headers: HeadersInit = {}) {
    this.#path = path;
    this.#headers = headers;
  }

  getUrl(params = '', queries = '') {
    return '/api' + this.#path + params + queries;
  }

  async get<T>(params?: string, queries?: string) {
    const response = await fetchApi(this.getUrl(params, queries), {
      method: 'GET',
      headers: this.#headers,
    });
    const data: T = await response.json();
    return data;
  }

  post<T>(body: T, params?: string, queries?: string) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'POST',
      body: JSON.stringify(body),
      headers: this.#headers,
    });
  }

  patch<T>(body: T, params?: string, queries?: string) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'PATCH',
      body: JSON.stringify(body),
      headers: this.#headers,
    });
  }

  delete(params?: string, queries?: string) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'DELETE',
      headers: this.#headers,
    });
  }
}
