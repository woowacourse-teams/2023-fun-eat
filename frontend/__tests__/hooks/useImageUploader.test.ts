import { useImageUploader } from '@/hooks/common';
import { renderHook, act } from '@testing-library/react';

const originalCreateObjectUrl = URL.createObjectURL;
const originalRevokeObjectUrl = URL.revokeObjectURL;

beforeAll(() => {
  URL.createObjectURL = jest.fn(() => 'mocked url');
  URL.revokeObjectURL = jest.fn();
});

afterAll(() => {
  URL.createObjectURL = originalCreateObjectUrl;
  URL.revokeObjectURL = originalRevokeObjectUrl;
});

it('uploadImage를 사용하여 이미지 파일을 업로드할 수 있다.', () => {
  const { result } = renderHook(() => useImageUploader());

  const file = new File(['dummy content'], 'example.png', { type: 'image/png' });

  act(() => {
    result.current.uploadImage(file);
  });

  expect(result.current.imageFile).toBe(file);
  expect(result.current.previewImage).toBe('mocked url');
  expect(URL.createObjectURL).toHaveBeenCalledWith(file);
});

it('이미지 파일이 아니면 "이미지 파일만 업로드 가능합니다." 메시지를 보여주는 alert 창이 뜬다.', () => {
  const { result } = renderHook(() => useImageUploader());

  const file = new File(['dummy content'], 'example.txt', { type: 'text/plain' });

  global.alert = jest.fn();

  act(() => {
    result.current.uploadImage(file);
  });

  expect(global.alert).toHaveBeenCalledWith('이미지 파일만 업로드 가능합니다.');
});

it('deleteImage를 사용하여 이미지 파일을 삭제할 수 있다.', () => {
  const { result } = renderHook(() => useImageUploader());

  const file = new File(['dummy content'], 'example.png', { type: 'image/png' });

  act(() => {
    result.current.uploadImage(file);
  });

  act(() => {
    result.current.deleteImage();
  });

  expect(result.current.imageFile).toBeNull();
  expect(result.current.previewImage).toBe('');
  expect(URL.revokeObjectURL).toHaveBeenCalledWith('mocked url');
});
