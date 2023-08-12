import 'dayjs/locale/ko';

import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';

dayjs.extend(relativeTime);

export const getRelativeDate = (date: string) => dayjs().locale('ko').to(dayjs(date));

export const getFormattedDate = (date: string) => dayjs(date).format('YYYY.MM.DD');
