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


// Delete Form
    document.addEventListener('submit', function (e) {
        const form = e.target;
        if (!form.matches('.delete-form')) return;

        if (!confirm('Are you sure you want to delete this task?')) {
            e.preventDefault();
            return;
        }

        e.preventDefault();

        const card = form.closest('.task-card');
        const url  = form.action;

        fetch(url, {
            method: 'POST',
            body: new FormData(form)
        })
            .then(res => {
                if (!res.ok) throw new Error('Delete failed');

                if (card) {
                    // on the task list page — animate and remove the card
                    card.style.transition = 'opacity 0.3s ease, transform 0.3s ease';
                    card.style.opacity    = '0';
                    card.style.transform  = 'scale(0.97)';
                    card.style.maxHeight  = card.offsetHeight + 'px';
                    card.style.overflow   = 'hidden';

                    setTimeout(() => {
                        card.style.transition  += ', max-height 0.35s ease, margin 0.35s ease';
                        card.style.maxHeight    = '0';
                        card.style.marginBottom = '0';
                    }, 280);

                    setTimeout(() => card.remove(), 650);
                } else {
                    // on the edit page — just redirect to task list
                    window.location.href = '/tasks';
                }
            })
            .catch(err => console.error('Delete failed:', err));
    });
});

// Theme Toggle
const themeToggle = document.getElementById('themeToggle');

// Load saved preference
const savedTheme = localStorage.getItem('theme') || 'light';
document.documentElement.setAttribute('data-theme', savedTheme);

if (themeToggle) {
    themeToggle.addEventListener('click', () => {
        const current = document.documentElement.getAttribute('data-theme');
        const next    = current === 'dark' ? 'light' : 'dark';
        document.documentElement.setAttribute('data-theme', next);
        localStorage.setItem('theme', next);
    });
}