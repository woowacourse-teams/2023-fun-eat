interface useFormDataProps<T> {
  imageKey: string;
  imageFile: File | File[] | null;
  formContentKey: string;
  formContent: T;
}

const useFormData = <T>({ imageKey, imageFile, formContentKey, formContent }: useFormDataProps<T>) => {
  const formData = new FormData();
  const formContentString = JSON.stringify(formContent);
  const formContentBlob = new Blob([formContentString], { type: 'application/json' });

  formData.append(formContentKey, formContentBlob);

  if (!imageFile) return formData;

  if (Array.isArray(imageFile)) {
    imageFile.forEach((file) => {
      formData.append(imageKey, file, file.name);
    });
    return formData;
  }

  formData.append(imageKey, imageFile, imageFile.name);

  return formData;
};

export default useFormData;
