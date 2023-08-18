import type { ChangeEventHandler, FormEventHandler, MouseEventHandler, RefObject } from 'react';
import { useRef, useState } from 'react';
import { useSearchParams } from 'react-router-dom';

const useSearch = () => {
  const inputRef = useRef<HTMLInputElement>(null);

  const [searchParams, setSearchParams] = useSearchParams();
  const currentSearchQuery = searchParams.get('query');

  const [searchQuery, setSearchQuery] = useState(currentSearchQuery || '');
  const [isSubmitted, setIsSubmitted] = useState(!!currentSearchQuery);

  const focusInput = () => {
    if (inputRef?.current) {
      inputRef.current.focus();
    }
  };

  const handleSearchQuery: ChangeEventHandler<HTMLInputElement> = (event) => {
    setIsSubmitted(false);
    setSearchQuery(event.currentTarget.value);
  };

  const handleSearch: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    const trimmedSearchQuery = searchQuery.trim();

    if (!trimmedSearchQuery) {
      alert('검색어를 입력해주세요');
      focusInput();
      setSearchQuery('');
      return;
    }

    if (currentSearchQuery === trimmedSearchQuery) {
      return;
    }

    setSearchQuery(trimmedSearchQuery);
    setIsSubmitted(true);
    setSearchParams({ query: trimmedSearchQuery });
  };

  const handleSearchClick: MouseEventHandler<HTMLButtonElement> = (event) => {
    const { value } = event.currentTarget;

    setSearchQuery(value);
    setIsSubmitted(true);
    setSearchParams({ query: value });
  };

  return { inputRef, searchQuery, isSubmitted, handleSearchQuery, handleSearch, handleSearchClick };
};

export default useSearch;
