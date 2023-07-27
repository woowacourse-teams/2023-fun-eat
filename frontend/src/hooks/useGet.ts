import { useEffect, useState } from 'react';

export const useGet = <T>(callback: () => Promise<Response>, dependency?: unknown) => {
  const [data, setData] = useState<T>();
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    request();
    if (error) {
      throw new Error(error);
    }
  }, [dependency, error]);

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
