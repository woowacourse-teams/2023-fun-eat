import type { ErrorResponse } from '@/types/response';

export const fetchApi = async (url: string, options: RequestInit) => {
  if (!navigator.onLine) {
    throw new Error('네트워크 오프라인이 감지되었습니다');
  }

  const response = await fetch(url, options);

  if (!response.ok) {
    const errorData: ErrorResponse = await response.json();
    throw new Error(errorData.message);
  }

  return response;
};
