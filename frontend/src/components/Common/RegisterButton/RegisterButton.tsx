import { Button } from '@fun-eat/design-system';
import styled from 'styled-components';

import { useMemberQuery } from '@/hooks/queries/members';

interface RegisterButtonProps {
  activeLabel: string;
  disabledLabel: string;
  onClick: () => void;
}

const RegisterButton = ({ activeLabel, disabledLabel, onClick }: RegisterButtonProps) => {
  const { data: member } = useMemberQuery();

  return (
    <RegisterButtonContainer
      type="button"
      customWidth="100%"
      customHeight="60px"
      color={member ? 'primary' : 'gray3'}
      textColor={member ? 'default' : 'white'}
      size="lg"
      weight="bold"
      onClick={onClick}
      disabled={!member}
    >
      {member ? activeLabel : disabledLabel}
    </RegisterButtonContainer>
  );
};

export default RegisterButton;

const RegisterButtonContainer = styled(Button)`
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
