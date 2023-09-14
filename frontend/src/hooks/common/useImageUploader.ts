import { useState } from 'react';

const isImageFile = (file: File) => file.type !== 'image/png' && file.type !== 'image/jpeg';

const useImageUploader = () => {
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [previewImage, setPreviewImage] = useState('');

  const uploadImage = (imageFile: File) => {
    if (isImageFile(imageFile)) {
      alert('이미지 파일만 업로드 가능합니다.');
      return;
    }

    setPreviewImage(URL.createObjectURL(imageFile));
    setImageFile(imageFile);
  };

  const deleteImage = () => {
    URL.revokeObjectURL(previewImage);
    setPreviewImage('');
    setImageFile(null);
  };

  return {
    previewImage,
    imageFile,
    uploadImage,
    deleteImage,
  };
};

export default useImageUploader;
