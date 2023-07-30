import { useEffect, useState } from 'react';

export const useMutate = <B>(callback: (body?: B) => Promise<Response>) => {
  const [isSuccess, setIsSuccess] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (error) {
      throw new Error(error);
    }
  }, [error]);

  const request = async (body?: B) => {
    try {
      const res = await callback(body);
      setIsSuccess(true);
      return res;
    } catch (error) {
      if (!(error instanceof Error)) {
        return;
      }
      setError(error.message);
      setIsSuccess(false);
    } finally {
      setIsLoading(false);
    }
  };

  return { request, isLoading, isSuccess, error };
};
