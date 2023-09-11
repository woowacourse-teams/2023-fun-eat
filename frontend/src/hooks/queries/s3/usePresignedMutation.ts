import { useMutation } from '@tanstack/react-query';

import { s3Api } from '@/apis';
import type { PresignedRequest } from '@/types/s3';

const headers = { 'Content-Type': 'application/json' };

const postPresigned = (body: PresignedRequest) => {
  return s3Api.post({ params: '/presigned' }, headers, body);
};

const usePresignedMutation = () => {
  return useMutation({
    mutationFn: (body: PresignedRequest) => postPresigned(body),
  });
};

export default usePresignedMutation;
