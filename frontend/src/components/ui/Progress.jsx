import React from 'react';

const Progress = ({ value = 0, className = '', ...props }) => {
  const percentage = Math.min(Math.max(value || 0, 0), 100);
  
  return (
    <div
      className={`relative h-4 w-full overflow-hidden rounded-full bg-secondary ${className}`}
      {...props}
    >
      <div
        className="h-full bg-primary transition-all duration-300"
        style={{ width: `${percentage}%` }}
      />
    </div>
  );
};

export default Progress;

