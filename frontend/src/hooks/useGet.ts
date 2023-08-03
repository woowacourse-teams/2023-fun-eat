import { useEffect, useState } from 'react';

export const useGet = <T>(callback: () => Promise<Response>, dependencies: unknown[] = []) => {
  const [data, setData] = useState<T>();
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    request();
    // TODO: 에러바운더리 추가
    //if (error) {
    //  throw new Error(error);
    //}
  }, [...dependencies]);

  const request = async () => {
    try {
      const response = await callback();
      const data: T = await response.json();

      setData(data);
    } catch (error) {
      if (!(error instanceof Error)) {
        return;
      }
      setError(error.message);
    } finally {
      setIsLoading(false);
    }
  };

  return { data, isLoading, error };
};
