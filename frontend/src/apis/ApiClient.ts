import { fetchApi } from './fetch';

interface RequestOptions {
  params?: string;
  queries?: string;
}

export class ApiClient {
  #path: string;

  #headers: HeadersInit;

  constructor(path: string, headers: HeadersInit = {}) {
    this.#path = path;
    this.#headers = headers;
  }

  getUrl(params = '', queries = '') {
    return 'http://3.36.100.213/api' + this.#path + params + queries;
  }

  get({ params, queries }: RequestOptions) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'GET',
      headers: this.#headers,
    });
  }

  post<B>({ params, queries }: RequestOptions, body?: B) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'POST',
      headers: this.#headers,
      body: body ? JSON.stringify(body) : null,
    });
  }

  postData({ params, queries }: RequestOptions, body?: FormData) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'POST',
      headers: this.#headers,
      body: body ? body : null,
    });
  }

  patch<B>({ params, queries }: RequestOptions, body?: B) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'PATCH',
      headers: this.#headers,
      body: body ? JSON.stringify(body) : null,
    });
  }

  delete<B>({ params, queries }: RequestOptions, body?: B) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'DELETE',
      headers: this.#headers,
      body: body ? JSON.stringify(body) : null,
    });
  }
}
