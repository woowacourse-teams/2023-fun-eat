import type { ReviewRequest } from '@/types/review';

interface useFormDataProps {
  imageKey: string;
  imageFile: File | null;
  formContentKey: string;
  formContent: ReviewRequest;
}

const useFormData = ({ imageKey, imageFile, formContentKey, formContent }: useFormDataProps) => {
  const formData = new FormData();

  if (imageFile) {
    formData.append(imageKey, imageFile, imageFile.name);
  }

  const formContentString = JSON.stringify(formContent);
  const formContentBlob = new Blob([formContentString], { type: 'application/json' });

  formData.append(formContentKey, formContentBlob);

  return { formData };
};

export default useFormData;
