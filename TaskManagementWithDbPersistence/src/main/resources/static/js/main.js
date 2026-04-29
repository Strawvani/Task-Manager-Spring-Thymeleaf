document.addEventListener('DOMContentLoaded', () => {

    document.querySelectorAll('.btn-complete').forEach(button => {
        button.addEventListener('click', async () => {
            const taskId  = button.getAttribute('data-task-id');
            const card    = button.closest('.task-card');

            try {
                const response = await fetch(`/tasks/${taskId}/complete`, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' }
                });
                if (response.ok) {
                    // Update UI without reloading
                    card.classList.add('done');
                    button.disabled = true;
                    button.textContent = 'Completed';
                } else {
                    alert('Failed to update task. Please try again.');
                }

            } catch (error) {
                console.error('Network error:', error);
                alert('Network error. Check your connection.');
            }
        });
    });

    // ── Character counter for description textarea ──────

    const textarea  = document.querySelector('textarea[name="description"]');
    const counter   = document.querySelector('#char-count');
    const MAX_CHARS = 500;

    if (textarea && counter) {
        textarea.addEventListener('input', () => {
            const remaining = MAX_CHARS - textarea.value.length;
            counter.textContent = `${remaining} characters remaining`;
            counter.style.color = remaining < 50 ? 'var(--color-danger)' : 'var(--color-muted)';
        });
    }

    const titleInput = document.querySelector('input[name="title"]');
    const titleCounter = document.querySelector('#title-counter');
    const MAX_TITLE_CHARS = 200;

    if (titleInput && titleCounter) {
        titleInput.addEventListener('input', () => {
            const remaining = MAX_TITLE_CHARS - titleInput.value.length;
            titleCounter.textContent = `${remaining} characters remaining`;
            titleCounter.style.color = remaining < 50 ? 'var(--color-danger)' : 'var(--color-muted)';
        });
    }



// ── Dynamic search/filter on task list ──────────────

    const searchInput = document.querySelector('#task-search');
    const taskCards   = document.querySelectorAll('.task-card');

    if (searchInput) {
        searchInput.addEventListener('input', () => {
            const query = searchInput.value.toLowerCase();
            taskCards.forEach(card => {
                const title = card.querySelector('.task-title').textContent.toLowerCase();
                card.style.display = title.includes(query) ? '' : 'none';
            });
        });
    }

});