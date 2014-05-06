<?php
namespace Cyniris\SyndicateBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="preference")
 */
class Preference {
    /**
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     * @ORM\Column(type="integer")
     */
    protected $id;

    /**
     * @ORM\Column(type="boolean", name="folder_level")
     */
    protected $folderLevel;

    /**
     * @ORM\Column(type="boolean")
     */
    protected $active;

    /**
     * @ORM\Column(type="boolean", name="display_text")
     */
    protected $displayText;

    /**
     * @ORM\Column(type="boolean", name="display_count")
     */
    protected $displayCount;

    /**
     * @ORM\Column(type="string", length=16, name="sort_order")
     */
    protected $sortOrder;

    /**
     * @ORM\ManyToOne(targetEntity="Feed")
     * @ORM\JoinColumn(name="feed_id", referencedColumnName="id", nullable=true)
     */
    protected $feed;

    /**
     * @ORM\ManyToOne(targetEntity="Feed")
     * @ORM\JoinColumn(name="folder_id", referencedColumnName="id", nullable=true)
     */
    protected $folder;

    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set folderLevel
     *
     * @param boolean $folderLevel
     * @return Preference
     */
    public function setFolderLevel($folderLevel)
    {
        $this->folderLevel = $folderLevel;

        return $this;
    }

    /**
     * Get folderLevel
     *
     * @return boolean 
     */
    public function getFolderLevel()
    {
        return $this->folderLevel;
    }

    /**
     * Set active
     *
     * @param boolean $active
     * @return Preference
     */
    public function setActive($active)
    {
        $this->active = $active;

        return $this;
    }

    /**
     * Get active
     *
     * @return boolean 
     */
    public function getActive()
    {
        return $this->active;
    }

    /**
     * Set displayText
     *
     * @param boolean $displayText
     * @return Preference
     */
    public function setDisplayText($displayText)
    {
        $this->displayText = $displayText;

        return $this;
    }

    /**
     * Get displayText
     *
     * @return boolean 
     */
    public function getDisplayText()
    {
        return $this->displayText;
    }

    /**
     * Set displayCount
     *
     * @param boolean $displayCount
     * @return Preference
     */
    public function setDisplayCount($displayCount)
    {
        $this->displayCount = $displayCount;

        return $this;
    }

    /**
     * Get displayCount
     *
     * @return boolean 
     */
    public function getDisplayCount()
    {
        return $this->displayCount;
    }

    /**
     * Set sortOrder
     *
     * @param string $sortOrder
     * @return Preference
     */
    public function setSortOrder($sortOrder)
    {
        $this->sortOrder = $sortOrder;

        return $this;
    }

    /**
     * Get sortOrder
     *
     * @return string 
     */
    public function getSortOrder()
    {
        return $this->sortOrder;
    }

    /**
     * Set feed
     *
     * @param \Cyniris\SyndicateBundle\Entity\Feed $feed
     * @return Preference
     */
    public function setFeed(\Cyniris\SyndicateBundle\Entity\Feed $feed = null)
    {
        $this->feed = $feed;

        return $this;
    }

    /**
     * Get feed
     *
     * @return \Cyniris\SyndicateBundle\Entity\Feed 
     */
    public function getFeed()
    {
        return $this->feed;
    }

    /**
     * Set folder
     *
     * @param \Cyniris\SyndicateBundle\Entity\Feed $folder
     * @return Preference
     */
    public function setFolder(\Cyniris\SyndicateBundle\Entity\Feed $folder = null)
    {
        $this->folder = $folder;

        return $this;
    }

    /**
     * Get folder
     *
     * @return \Cyniris\SyndicateBundle\Entity\Feed 
     */
    public function getFolder()
    {
        return $this->folder;
    }
}
