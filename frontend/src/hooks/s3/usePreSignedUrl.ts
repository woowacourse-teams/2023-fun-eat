import { usePresignedMutation } from '../queries/s3';

import type { PresignedResponse } from '@/types/s3';

const IMAGE_ENVIRONMENT = window.location.href.includes('dev') ? 'dev' : 'prod';

const usePreSignedUrl = (file: File) => {
  const { mutateAsync } = usePresignedMutation();

  const imageUrl = `${process.env.CLOUDFRONT_URL}/${IMAGE_ENVIRONMENT}/${file.name}`;

  const getPreSignedUrl = async () => {
    const response = await mutateAsync({ fileName: file.name });
    const { preSignedUrl }: PresignedResponse = await response.json();

    return preSignedUrl;
  };

  const putFileToS3 = (url: string) => {
    fetch(url, {
      method: 'PUT',
      body: file,
      headers: {
        'Content-Type': file.type,
      },
    });
  };

  return { getPreSignedUrl, putFileToS3, imageUrl };
};

export default usePreSignedUrl;
