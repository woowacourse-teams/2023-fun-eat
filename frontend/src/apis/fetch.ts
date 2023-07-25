export const fetchApi = async (url: string, options: RequestInit) => {
  if (!navigator.onLine) {
    throw new Error('네트워크 오프라인이 감지되었습니다');
  }

  const response = await fetch(url, options);

  if (!response.ok) {
    throw new Error(`에러 발생 상태코드:${response.status}`);
  }

  return response;
};
