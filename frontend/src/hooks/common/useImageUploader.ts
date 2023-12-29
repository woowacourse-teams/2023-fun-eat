import { useToastActionContext } from '@fun-eat/design-system';
import imageCompression from 'browser-image-compression';
import { useState } from 'react';

const isImageFile = (file: File) => file.type !== 'image/png' && file.type !== 'image/jpeg';

const options = {
  maxSizeMB: 1,
  maxWidthOrHeight: 1920,
  useWebWorker: true,
};

const useImageUploader = () => {
  const { toast } = useToastActionContext();

  const [imageFile, setImageFile] = useState<File | null>(null);
  const [isImageUploading, setIsImageUploading] = useState(false);
  const [previewImage, setPreviewImage] = useState('');

  const uploadImage = async (imageFile: File) => {
    if (isImageFile(imageFile)) {
      toast.error('이미지 파일만 업로드 가능합니다.');
      return;
    }

    setPreviewImage(URL.createObjectURL(imageFile));

    try {
      setIsImageUploading(true);

      const compressedFile = await imageCompression(imageFile, options);
      const compressedImageFilePromise = imageCompression.getFilefromDataUrl(
        await imageCompression.getDataUrlFromFile(compressedFile),
        compressedFile.name
      );
      compressedImageFilePromise
        .then((result) => {
          setImageFile(result);
        })
        .then(() => {
          setIsImageUploading(false);
          toast.success('이미지가 성공적으로 등록 됐습니다');
        });
    } catch (error) {
      console.log(error);
    }
  };

  const deleteImage = () => {
    URL.revokeObjectURL(previewImage);
    setPreviewImage('');
    setImageFile(null);
  };

  return {
    isImageUploading,
    previewImage,
    imageFile,
    uploadImage,
    deleteImage,
  };
};

export default useImageUploader;
