import { useState } from 'react';

import { uuid } from '@/utils/uuid';

const isImageFile = (file: File) => file.type !== 'image/png' && file.type !== 'image/jpeg';

const editImageFileName = (imageFile: File) => {
  const fileName = imageFile.name;
  const fileExtension = fileName.split('.').pop();
  const newFileName = `${uuid()}.${fileExtension}`;

  return new File([imageFile], newFileName, { type: imageFile.type });
};

const useImageUploader = () => {
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [previewImage, setPreviewImage] = useState('');

  const uploadImage = (imageFile: File) => {
    if (isImageFile(imageFile)) {
      alert('이미지 파일만 업로드 가능합니다.');
      return;
    }

    const editedImageFile = editImageFileName(imageFile);

    setPreviewImage(URL.createObjectURL(editedImageFile));
    setImageFile(editedImageFile);
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
