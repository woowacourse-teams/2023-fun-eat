import type { ChangeEventHandler, FormEventHandler, MouseEventHandler } from 'react';
import { useRef, useState } from 'react';
import { useSearchParams } from 'react-router-dom';

const useSearch = () => {
  const inputRef = useRef<HTMLInputElement>(null);

  const [searchParams, setSearchParams] = useSearchParams();
  const currentSearchQuery = searchParams.get('query');

  const [searchQuery, setSearchQuery] = useState(currentSearchQuery || '');
  const [isSubmitted, setIsSubmitted] = useState(!!currentSearchQuery);
  const [isAutocompleteOpen, setIsAutocompleteOpen] = useState(searchQuery.length > 0);

  const focusInput = () => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  const handleSearchQuery: ChangeEventHandler<HTMLInputElement> = (event) => {
    setIsSubmitted(false);
    setSearchQuery(event.currentTarget.value);
    setIsAutocompleteOpen(event.currentTarget.value.length > 0);
  };

  const handleSearch: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    const trimmedSearchQuery = searchQuery.trim();

    if (!trimmedSearchQuery) {
      alert('검색어를 입력해주세요');
      focusInput();
      resetSearchQuery();
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

  const handleAutocompleteClose = () => {
    setIsAutocompleteOpen(false);
  };

  const resetSearchQuery = () => {
    setSearchQuery('');
  };

  return {
    inputRef,
    searchQuery,
    isSubmitted,
    isAutocompleteOpen,
    handleSearchQuery,
    handleSearch,
    handleSearchClick,
    handleAutocompleteClose,
    resetSearchQuery,
  };
};

export default useSearch;
