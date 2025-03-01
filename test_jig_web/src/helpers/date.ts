import { addHours, format, parseISO } from 'date-fns';

export const formatDateTime = (dateString: string): string => {
    const date = parseISO(dateString);
    const adjustedDate = addHours(date, 5.5);
    return format(adjustedDate, 'yyyy-MM-dd hh:mm:ss a');
};

export const formatDate = (dateString: string): string => {
    return format(parseISO(dateString), 'yyyy-MM-dd');
};