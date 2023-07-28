import { Button, Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import type { SortOption } from '@/types/common';

interface SortButtonProps {
  option: SortOption;
  onClick: () => void;
}

const SortButton = ({ option, onClick }: SortButtonProps) => {
  const theme = useTheme();

  return (
    <SortButtonContainer type="button" weight="bold" textColor="info" variant="transparent" onClick={onClick}>
      <SvgIcon variant="sort" color={theme.textColors.info} width={18} height={18} />
      {option.label}
    </SortButtonContainer>
  );
};

export default SortButton;

const SortButtonContainer = styled(Button)`
  display: flex;
  align-items: center;
  justify-content: flex-end;
  column-gap: 4px;
  padding: 0;
`;
