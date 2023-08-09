import { Checkbox } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';

import { useReviewFormActionContext } from '@/hooks/context';
import useEnterKeyDown from '@/hooks/useEnterKeyDown';

const RebuyCheckbox = () => {
  const { handleReviewFormValue } = useReviewFormActionContext();
  const { inputRef, labelRef, handleKeydown } = useEnterKeyDown();

  const handleRebuy: ChangeEventHandler<HTMLInputElement> = (event) => {
    handleReviewFormValue({ target: 'rebuy', value: event.target.checked });
  };

  return (
    <p onKeyDown={handleKeydown}>
      <Checkbox ref={labelRef} inputRef={inputRef} weight="bold" onChange={handleRebuy} tabIndex={0}>
        재구매할 생각이 있으신가요?
      </Checkbox>
    </p>
  );
};

export default RebuyCheckbox;
