import { usePresignedMutation } from '../queries/s3';

import type { PresignedResponse } from '@/types/s3';

const ENVIRONMENT = window.location.href.includes('dev') ? 'dev' : 'prod';
const UPLOAD_ERROR_MESSAGE = '이미지 업로드에 실패했습니다. 다시 시도해주세요.';

const putFileToS3 = (url: string, file: File) => {
  fetch(url, {
    method: 'PUT',
    body: file,
    headers: {
      'Content-Type': file.type,
    },
  });
};

const useS3Upload = (file: File | null) => {
  const { mutateAsync } = usePresignedMutation();

  const getPreSignedUrl = async (file: File) => {
    const response = await mutateAsync({ fileName: file.name });
    const { preSignedUrl }: PresignedResponse = await response.json();

    return preSignedUrl;
  };

  const fileUrl = file !== null ? `${process.env.CLOUDFRONT_URL}/${ENVIRONMENT}/${file.name}` : null;

  const uploadToS3 = async () => {
    try {
      if (file !== null) {
        const preSignedUrl = await getPreSignedUrl(file);
        putFileToS3(preSignedUrl, file);
      }
    } catch {
      throw new Error(UPLOAD_ERROR_MESSAGE);
    }
  };

  return { uploadToS3, fileUrl };
};

export default useS3Upload;
