import { useState } from 'react';

import { uuid } from '@/utils/uuid';

const useImageUploader = () => {
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [previewImage, setPreviewImage] = useState('');
  const [fileName, setFileName] = useState('');

  const editImageFileName = (imageFile: File) => {
    const fileName = imageFile.name;
    const fileExtension = fileName.split('.').pop();
    const newFileName = `${uuid()}.${fileExtension}`;

    setFileName(newFileName);
    return new File([imageFile], newFileName, { type: imageFile.type });
  };

  const uploadImage = (imageFile: File) => {
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
    fileName,
    uploadImage,
    deleteImage,
  };
};

export default useImageUploader;
