import imageCompression from 'browser-image-compression';
import type { ChangeEventHandler } from 'react';
import { useState } from 'react';

const MAX_SIZE = 5 * 1024 * 1024;

const useReviewImageUploader = () => {
  const [reviewPreviewImage, setReviewPreviewImage] = useState('');
  const [reviewImageFile, setReviewImageFile] = useState<File | null>(null);

  const options = {
    maxSizeMB: 1,
    maxWidthOrHeight: 1920,
    useWebWorker: true,
  };

  const uploadReviewImage: ChangeEventHandler<HTMLInputElement> = async (event) => {
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
      const compressedFile = await imageCompression(imageFile, options);
      const compressedImageFilePromise = imageCompression.getFilefromDataUrl(
        await imageCompression.getDataUrlFromFile(compressedFile),
        compressedFile.name
      );
      compressedImageFilePromise.then((result) => {
        setReviewImageFile(result);
      });
    } catch (error) {
      console.log(error);
    }

    setReviewPreviewImage(URL.createObjectURL(imageFile));
  };

  const deleteReviewImage = () => {
    URL.revokeObjectURL(reviewPreviewImage);
    setReviewPreviewImage('');
  };

  return {
    reviewPreviewImage,
    setReviewPreviewImage,
    reviewImageFile,
    uploadReviewImage,
    deleteReviewImage,
  };
};

export default useReviewImageUploader;
