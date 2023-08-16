import { Button, Heading, Spacing } from '@fun-eat/design-system';
import type { ChangeEventHandler, FormEventHandler } from 'react';
import { useState } from 'react';
import styled from 'styled-components';

import { Input, SectionTitle, SvgIcon } from '@/components/Common';
import { useFormData, useImageUploader } from '@/hooks/common';
import { useMemberValueContext } from '@/hooks/context';
import { useMemberModifyMutation } from '@/hooks/queries/members';
import type { MemberRequest } from '@/types/member';

const MemberModifyPage = () => {
  const { previewImage, imageFile, uploadImage } = useImageUploader();
  const member = useMemberValueContext();
  const [nickname, setNickname] = useState(member?.nickname);

  if (!nickname) {
    return;
  }

  const { mutate } = useMemberModifyMutation();

  const modifyNickname: ChangeEventHandler<HTMLInputElement> = (event) => {
    setNickname(event.target.value);
  };

  const formData = useFormData<MemberRequest>({
    imageKey: 'image',
    imageFile: imageFile,
    formContentKey: 'memberRequest',
    formContent: { nickname },
  });

  const handleSubmit: FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();

    await mutate(formData);
  };

  return (
    <>
      <SectionTitle name="" />
      <MemberForm onSubmit={handleSubmit}>
        <div>
          <MemberImageUploaderContainer>
            <MemberImageUploaderWrapper>
              <UserProfileImageWrapper>
                {previewImage ? (
                  <ProfileImage src={previewImage} alt="업로드한 사진" width={80} />
                ) : (
                  <SvgIcon variant="modifyProfile" width={80} height={80} />
                )}
              </UserProfileImageWrapper>
              <UserImageUploaderLabel>
                <input type="file" accept="image/*" onChange={uploadImage} />
                <SvgIcon variant="camera" width={20} height={20} />
              </UserImageUploaderLabel>
            </MemberImageUploaderWrapper>
          </MemberImageUploaderContainer>
          <Spacing size={44} />
          <Heading as="h2" size="xl" tabIndex={0}>
            닉네임
          </Heading>
          <Spacing size={12} />
          <Input value={nickname} customWidth="100%" onChange={modifyNickname} />
        </div>
        <FormButton customWidth="100%" customHeight="60px" size="xl" weight="bold">
          수정하기
        </FormButton>
      </MemberForm>
    </>
  );
};

export default MemberModifyPage;

const MemberImageUploaderContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const MemberImageUploaderWrapper = styled.div`
  position: relative;
`;

const UserProfileImageWrapper = styled.div`
  width: 80px;
  height: 80px;
  background: ${({ theme }) => theme.backgroundColors.default};
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-radius: 50%;
  overflow: hidden;
`;

const ProfileImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const UserImageUploaderLabel = styled.label`
  position: absolute;
  top: 54px;
  right: -5px;
  width: 30px;
  height: 30px;
  background: ${({ theme }) => theme.backgroundColors.default};
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-radius: 50%;
  text-align: center;
  cursor: pointer;

  & > input {
    display: none;
  }

  & > svg {
    transform: translateX(-0.5px) translateY(3px);
  }
`;

const MemberForm = styled.form`
  height: 92%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const FormButton = styled(Button)`
  background: ${({ theme }) => theme.colors.primary};
  color: ${({ theme }) => theme.colors.black};
`;
