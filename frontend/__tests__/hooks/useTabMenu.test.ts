import { useTabMenu } from '@/hooks/common';
import { renderHook, act } from '@testing-library/react';

it('선택된 탭 초기 상태는 0번 인덱스이다.', () => {
  const { result } = renderHook(() => useTabMenu());

  expect(result.current.selectedTabMenu).toBe(0);
  expect(result.current.isFirstTabMenu).toBe(true);
});

it('handleTabMenuClick를 사용하여 선택한 탭 인덱스를 저장할 수 있다. ', () => {
  const { result } = renderHook(() => useTabMenu());

  act(() => {
    result.current.handleTabMenuClick(1);
  });

  expect(result.current.selectedTabMenu).toBe(1);
});

it('initTabMenu를 사용하여 선택된 탭을 맨 처음 탭으로 초기화할 수 있다.', () => {
  const { result } = renderHook(() => useTabMenu());

  act(() => {
    result.current.handleTabMenuClick(1);
    result.current.initTabMenu();
  });

  expect(result.current.selectedTabMenu).toBe(0);
});
