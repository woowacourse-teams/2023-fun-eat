import { useEffect, useState } from 'react';

export const useGet = <T>(callback: () => Promise<T>, dependencies: unknown[] = []) => {
  const [data, setData] = useState<T>();
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    request();
    if (error) {
      throw new Error(error);
    }
  }, [...dependencies, error]);

  const request = async () => {
    try {
      const data = await callback();
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
