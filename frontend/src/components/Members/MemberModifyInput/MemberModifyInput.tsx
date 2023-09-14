import { Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import styled from 'styled-components';

import { Input } from '@/components/Common';

const MIN_LENGTH = 1;
const MAX_LENGTH = 10;

interface MemberModifyInputProps {
  nickname: string;
  modifyNickname: ChangeEventHandler<HTMLInputElement>;
}

const MemberModifyInput = ({ nickname, modifyNickname }: MemberModifyInputProps) => {
  const theme = useTheme();

  return (
    <MemberModifyInputContainer>
      <Heading as="h2" size="xl" tabIndex={0}>
        닉네임
      </Heading>
      <NicknameStatusText color={theme.textColors.info} tabIndex={0}>
        {nickname.length}자 / {MAX_LENGTH}자
      </NicknameStatusText>
      <Spacing size={12} />
      <Input
        value={nickname}
        customWidth="100%"
        onChange={modifyNickname}
        minLength={MIN_LENGTH}
        maxLength={MAX_LENGTH}
      />
    </MemberModifyInputContainer>
  );
};

export default MemberModifyInput;

const MemberModifyInputContainer = styled.div`
  position: relative;
`;

const NicknameStatusText = styled(Text)`
  position: absolute;
  top: 0;
  right: 0;
`;
