import useStarRating from '@/hooks/useStarRate';
import { renderHook, act } from '@testing-library/react';

it('handleRating을 사용하여 별점을 저장할 수 있다.', () => {
  const { result } = renderHook(() => useStarRating());

  expect(result.current.rating).toBe(0);

  act(() => {
    result.current.handleRating(3);
  });

  expect(result.current.rating).toBe(3);
});

it('handleMouseEnter를 사용하여 마우스 호버된 별점 값을 저장할 수 있다.', () => {
  const { result } = renderHook(() => useStarRating());

  expect(result.current.hovering).toBe(0);

  act(() => {
    result.current.handleMouseEnter(3);
  });

  expect(result.current.hovering).toBe(3);
});

it('handleMouseLeave를 사용하여 마우스 호버된 별점을 초기화 할 수 있다.', () => {
  const { result } = renderHook(() => useStarRating());

  expect(result.current.hovering).toBe(0);

  act(() => {
    result.current.handleMouseEnter(3);
  });

  expect(result.current.hovering).toBe(3);

  act(() => {
    result.current.handleMouseLeave();
  });

  expect(result.current.hovering).toBe(0);
});
