import { fetchApi } from './fetch';

interface RequestOptions {
  params?: string;
  queries?: string;
  credentials?: boolean;
}

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

  get({ params, queries, credentials = false }: RequestOptions) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'GET',
      headers: this.#headers,
      credentials: credentials ? 'include' : 'omit',
    });
  }

  post<B>({ params, queries, credentials = false }: RequestOptions, body?: B) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'POST',
      headers: this.#headers,
      body: body ? JSON.stringify(body) : null,
      credentials: credentials ? 'include' : 'omit',
    });
  }

  postData({ params, queries, credentials = false }: RequestOptions, body?: FormData) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'POST',
      headers: this.#headers,
      body: body ? body : null,
      credentials: credentials ? 'include' : 'omit',
    });
  }

  patch<B>({ params, queries, credentials = false }: RequestOptions, body?: B) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'PATCH',
      headers: this.#headers,
      body: body ? JSON.stringify(body) : null,
      credentials: credentials ? 'include' : 'omit',
    });
  }

  delete<B>({ params, queries, credentials = false }: RequestOptions, body?: B) {
    return fetchApi(this.getUrl(params, queries), {
      method: 'DELETE',
      headers: this.#headers,
      body: body ? JSON.stringify(body) : null,
      credentials: credentials ? 'include' : 'omit',
    });
  }
}
