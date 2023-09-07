import type { ChangeEventHandler } from 'react';
import { useState } from 'react';

import { PutObjectCommand, S3Client } from '@aws-sdk/client-s3';

const MAX_SIZE = 5 * 1024 * 1024;
const client = new S3Client({});
const IMAGE_DIRECTORY = window.location.href.includes('dev') ? 'dev' : 'prod';

const useImageUploader = () => {
  const [previewImage, setPreviewImage] = useState('');
  const [imageUrl, setImageUrl] = useState<string | null>(null);

  const uploadImage: ChangeEventHandler<HTMLInputElement> = async (event) => {
    if (!event.target.files) {
      return;
    }

    const imageFile = event.target.files[0];

    if (imageFile.size > MAX_SIZE) {
      alert('이미지 크기가 너무 커요. 5MB 이하의 이미지를 골라주세요.');
      event.target.value = '';
      return;
    }

    try {
      const image = new PutObjectCommand({
        Bucket: process.env.S3_BUCKET_NAME,
        Key: `${process.env.S3_DIRECTORY}/${IMAGE_DIRECTORY}/${imageFile.name}`,
        Body: imageFile,
      });

      setPreviewImage(URL.createObjectURL(imageFile));
      await client.send(image);
      setImageUrl(`${process.env.CLOUDFRONT_URL}/${IMAGE_DIRECTORY}/${imageFile.name}`);
    } catch (error) {
      alert('이미지 업로드에 실패했습니다. 다시 시도해주세요.');
    }
  };

  const deleteImage = () => {
    URL.revokeObjectURL(previewImage);
    setPreviewImage('');
    setImageUrl(null);
  };

  return {
    previewImage,
    imageUrl,
    uploadImage,
    deleteImage,
  };
};

export default useImageUploader;
